(() => {
    'use strict';


    const config = require('./config');
    const knex = require('knex')(config.production);
    const md5 = require('md5');
    const fs = require('node:fs');
    const jwt = require('jsonwebtoken');

    const private_key = fs.readFileSync('./keys/private.key');
    const public_cert = fs.readFileSync('./keys/public.pem');


    exports.create = async(username) => {
        try{
            let queryResult = await knex('Sessions')
                .select('username')
                .where('username', username)
                .then((rows) => {
                    return rows;
                });
            
            console.log("QUERY_RESULT " + JSON.stringify(queryResult));
            
            let date = new Date()
            let also = md5(`${date.getMilliseconds}_${username}_secreto_random`)
            if(queryResult.length == 0){
                /// No existe el usuario con una sesion activa ni previa
                /// Creamos un registro nuevo en la tabla
                await knex('Sessions')
                    .insert({username, also});
            } else {
                // Actualizamos nuestro secreto
                await knex('Sessions')
                    .update({also})
                    .where('username', username);
            }

            let token = jwt.sign({
                data: also,
                username
            }, private_key, {
                expiresIn: '60m',
                algorithm: 'RS512',
            });

            console.log("JWT " + JSON.stringify(token))

            return {
                token
            }
        }catch(e){
            console.log(e);
            console.log(JSON.stringify(e));
            return {
                token: null
            };
        }
    }

    exports.verify = async(token) => {
        try {
            let promise = new Promise((resolve) => {
                jwt.verify(token, public_cert, { algorithms: 'RS512' }, async (err, decoded) => {
                    
                    let response = {
                        isValid: false,
                        message: '',
                        username: null
                    }

                    if(err){
                        resolve( {...response, message: err.message} );
                        return;
                    }
                    let queryResult = await knex('Sessions')
                        .select('also')
                        .where('username', decoded.username)
                        .then((rows) => {
                            return rows;
                        })
                        .catch((e) => {
                            resolve({...response, message: "Sesión no comprobada"});
                            throw e;
                        });
                    if(queryResult.length == 0){
                        resolve( {...response, message: "Usuario sin sesión iniciada"} );
                        return;
                    }
                    
                    if(queryResult[0].also == decoded.data){
                        /// La firma random por sesion es correcta
                        resolve({...response, isValid: true, username: decoded.username});
                        return;
                    }
                    resolve({...response, message: "Sesión comprometida, inicie sesión nuevamente para obtener un Token nuevo"});
                });
            });
            let result = await promise.then();
            return result;
        }catch(e) {
            console.log(e);
            return { isValid: false, message: "Sesión no comprobada", username: null};
        }
    }
})();