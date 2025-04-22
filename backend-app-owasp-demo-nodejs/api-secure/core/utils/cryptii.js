(() => {
    'use strict';


    const NodeRSA = require('node-rsa');
    const fs = require('node:fs');

    const private_key = fs.readFileSync(__dirname + '/keys/private.key');
    const public_cert = fs.readFileSync(__dirname + '/keys/public.pem');

    const rsaPrivate = new NodeRSA(private_key);

    const rsaPublic = new NodeRSA(public_cert);

    exports.decrypt = async (value) => {
        try{
            rsaPrivate.setOptions({ encryptionScheme: 'pkcs1' });
            return rsaPrivate.decrypt(value, 'utf8');
        }catch(e){
            console.log(e);
            throw value;
        }
    }


    exports.encrypt = async (value) => {
        try{
            rsaPublic.setOptions({ encryptionScheme: 'pkcs1' });
            return rsaPublic.encrypt(value, 'base64').toString();
        }catch(e){
            console.log(e);
            throw value;
        }
    }

    exports.validationSchema = async (data, schema) => {
        let response = {
            isValid: false,
            payload: null,
            error: null
        }
        try{
            let stringDecrypted = await this.decrypt(data);

            let dataDecrypted = JSON.parse(stringDecrypted);
            
            const validationResult = schema.validate(dataDecrypted);
            
            if (validationResult.error) {
                console.error('Validation error:', validationResult.error.message);
                return {
                    ...response,
                    payload: dataDecrypted,
                    error: validationResult.error.message
                };
            } else {
                return {
                    ...response,
                    payload: dataDecrypted,
                    isValid: true
                };
            }
        }catch(e){
            return {
                ...response,
                payload: data,
                error: e.message
            };
        }
    }
})();