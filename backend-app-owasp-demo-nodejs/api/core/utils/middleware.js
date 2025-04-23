(() => {
    'use strict';
    const Boom = require('@hapi/boom');
    const cryptii = require('./cryptii');
    
    
    /// Validamos la API-KEY-X
    const _validateKey = async (req, h) => {
        const isKeyValid = req.headers['api-key-x'] === 'Alza_safd9209jfw893293823';
        if(!isKeyValid) {
            throw Boom.unauthorized("API-KEY-X no válida")
        }
        return true;
    }

    const _verifyToken = async(req, h) => {

        const authorization = req.headers['authorization'] || req.headers['AUTHOTIZATION'] || req.headers['Authorization'];
        const methods = req.server.methods;

        if(typeof authorization !== 'undefined') {
            if(authorization.startsWith("Bearer ")){
                try {
                    const bearer = authorization.split(' ');
                    const token = bearer[1];
    
                    let result = await methods.session.verify(
                        token
                    );
    
                    if(!result.isValid) {
                        throw Boom.unauthorized(result.message);
                    }
                    return result.isValid;
                }catch(e){
                    if(e.isBoom && e.output.statusCode == 401){
                        throw Boom.unauthorized(e.output.message || e.output.payload.message);
                    }
                    throw Boom.notImplemented("Ocurrio un error al procesar el token Bearer")
                }
            } else {
                throw Boom.notImplemented("El token no tiene la estructura Bearer");
            }            
        } else {
            throw Boom.forbidden("Es necesario el header Authorization");
        }
    }

    const _validateServer = async(req, h) => {
        try {
            return req.server.info.protocol === 'https' && req.server.info.port == process.env.PORT_HTTPS;
        } catch(e){
            throw Boom.internal();
        }
    }

    exports.validateServer = { method: _validateServer, assign: 'isSecure' }

    exports.verifyToken = { method: _verifyToken, assign: 'isAuthorization' }

    exports.validationApiKeyX = { method: _validateKey, assign: 'isApiKeyValid' }

    exports.validationSchema = (schema) => {
        return {
            method: async(req, h) => {
                try{
                    const resultValidationSchema = await cryptii.validationSchema(req.payload, schema);
                     /// Si existe algun error se indica al usuario
                     if(!resultValidationSchema.isValid){
                        throw Boom.badRequest(resultValidationSchema.error);
                    }
                    return resultValidationSchema.payload;
                }catch(e) {
                    if(e.isBoom && e.output.statusCode == 400){
                        throw Boom.badRequest(e.output.message || e.output.payload.message);
                    }
                    throw Boom.notImplemented("Error con el esquema: " + JSON.stringify(schema))
                }
            },
            assign: 'payload'
        };
    }
})();