(() => {
    'use strict';
    const Boom = require('@hapi/boom');
    const _ = require('underscore');
    
    let _roles = null;

    const init = async (server) => {
        if(_roles == null){
            _roles = await server.methods.catalogs.getAllRoles();
        }
    }

    const validateKey = (request) => {
        return request.headers['api-key-x'] === 'Alza_safd9209jfw893293823';
    };

    exports.getAll = async(req, h) => {
        try{
            await init(req.server);
            const users = await req.server.methods.users.getAll();
            for(let i = 0; i < users.length; i++){
                users[i].rol = _roles[_.findIndex(_roles, {id: users[i].rol_id})].description;
            }
            return users;
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

    exports.insert = async(req, h) => {
        try{
            return await req.server.methods.users.insert(req.payload);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

    exports.signInInsecure = async(req, h) => {
        try{
            // List
            // String - message error
            
            const respuesta =  await req.server.methods.users.signon(req.payload.username, req.payload.password);

            if(typeof respuesta == "string"){
                return Boom.unauthorized(respuesta);
            } else if(typeof respuesta == "object"){
                return {
                    statusCode: 200,
                    error: null,
                    message: "Success",
                    data: respuesta
                };
            }
            throw respuesta;

        }catch(e){
            throw Boom.internal(JSON.stringify(e));
        }
    }

    exports.signInSecure = async (req, h) => {
        try {
            const isKeyValid = validateKey(req);
            if(!isKeyValid) {
                return Boom.unauthorized("API-KEY-X no valida");
            }

            const respuesta =  await req.server.methods.users.signon(req.payload.username, req.payload.password);
            if(typeof respuesta == "string"){
                return Boom.unauthorized(respuesta);
            } else if(typeof respuesta == "object"){
                let session = await req.server.methods.session.create(req.payload.username);

                console.log("sesion " + JSON.stringify(session));

                respuesta.token = session.token;

                return {
                    statusCode: 200,
                    error: null,
                    message: "Success",
                    data: respuesta
                };
            }
            throw respuesta;

        }catch(e){
            console.log(e);
            throw Boom.internal(JSON.stringify(e));
        }
    }
})();