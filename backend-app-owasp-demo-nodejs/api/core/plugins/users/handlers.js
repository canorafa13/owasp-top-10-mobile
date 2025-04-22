(() => {
    'use strict';
    const Boom = require('@hapi/boom');
    const PasswordValidator = require('password-validator');
    const cryptii = require('../../utils/cryptii');
    const baseResponse = require('../../schemas/baseResponse');

    exports.cryptii = async(req, h) => {
        try{
            let respuesta = null
            if(req.payload.type == 1) {
                respuesta = await cryptii.encrypt(req.payload.word);
            } else {
                respuesta = await cryptii.decrypt(req.payload.word);
            }
            return baseResponse.success(respuesta);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }


    exports.signUp = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const methods = req.server.methods;
            const payload = req.pre.payload || req.payload;

            if(isSecure){
                /// Validar que el password sea valido
                let schema = new PasswordValidator();

                schema
                    .is().min(8)                                    // Minimum length 8
                    .is().max(30)                                  // Maximum length 30
                    .has().uppercase(1)                              // Must have uppercase letters
                    .has().lowercase(1)                              // Must have lowercase letters
                    .has().digits(1)                                // Must have at least 1 digits
                    .has().not().spaces()                           // Should not have spaces
                    .has().symbols(1)
                    .is().not().oneOf(['Passw0rd', 'Password123']); // Blacklist these values

                let isValidPassword = schema.validate(payload.password, { details: true });
                if(isValidPassword.length != 0){
                    return Boom.badRequest(JSON.stringify(isValidPassword));
                }
            }

            payload.rol_id = 3;

            let respuesta = await methods.users.signUp(payload);
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(respuesta));
            }
            return baseResponse.success(respuesta);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

    exports.signIn = async (req, h) => {
        try {
            const isSecure = req.pre.isSecure || false;
            const methods = req.server.methods;
            const payload = req.pre.payload || req.payload;
            /// Buscamos al usuario en la base de datos
            let respuesta =  await methods.users.signIn(
                payload.username, 
                payload.password
            );

            /// Si tenemos una respuesta de tipo cadena, es un error
            if(typeof respuesta == "string"){
                return Boom.unauthorized(respuesta);
            } else if(typeof respuesta == "object"){
                /// Si es un objeto creamos un token de session
                if(isSecure){
                    let session = await methods.session.create(
                        payload.username
                    );

                    respuesta.token = session.token;

                    respuesta = await cryptii.encrypt(respuesta);
                }

                /// Retornamos la respuesta con un token de sesion
                return baseResponse.success(respuesta);
            }
            /// Si la respuesta es distinta, mandamos un Exception
            throw respuesta;

        }catch(e){
            console.log(e)
            throw Boom.notImplemented();
        }
    }
})();