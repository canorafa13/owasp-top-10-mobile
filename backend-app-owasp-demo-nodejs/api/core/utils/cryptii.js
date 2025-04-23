(() => {
    'use strict';


    const NodeRSA = require('node-rsa');
    const fs = require('node:fs');

    const crypto = require('crypto');

    const { randomBytes } = require('node:crypto');

    const private_key = fs.readFileSync(__dirname + '/keys/private.key');
    const public_cert = fs.readFileSync(__dirname + '/keys/public.pem');

    const rsaPrivate = new NodeRSA(private_key);

    const rsaPublic = new NodeRSA(public_cert);

    const utf8 = 'utf8'
    const base64 = 'base64'

    const _decryptRSA = async(value) => {
        try{
            rsaPrivate.setOptions({ encryptionScheme: 'pkcs1' });
            return rsaPrivate.decrypt(value, utf8);
        }catch(e){
            console.log(e);
            throw value;
        }
    }

    const _encryptRSA = async (value) => {
        try{
            rsaPublic.setOptions({ encryptionScheme: 'pkcs1' });
            return rsaPublic.encrypt(value, base64).toString();
        }catch(e){
            console.log(e);
            throw value;
        }
    }

    exports.decrypt = async (encryptedText, pass) => {
        try{
            let password = await _decryptRSA(pass);

            let keyString = password.substring(0, 32);
            let ivString = password.substring(password.length - 16);
            const key = Buffer.from(keyString, utf8);
            const iv = Buffer.from(ivString, utf8);
            const decipher = crypto.createDecipheriv('aes-256-cbc', key, iv);
            let decrypted = decipher.update(encryptedText, base64, utf8);
            decrypted += decipher.final(utf8);
            return decrypted;
        }catch(e){
            console.log(e);
            throw encryptedData;
        }
    }


    exports.encrypt = async (value) => {
        try{
            let randomKey = randomBytes(48 / 2).toString("hex");
            let keyString = randomKey.substring(0, 32);
            let ivString = randomKey.substring(randomKey.length - 16);

            const key = Buffer.from(keyString, utf8);
            const iv = Buffer.from(ivString, utf8);
            const cipher = crypto.createCipheriv('aes-256-cbc', key, iv);
            let encrypted = cipher.update(JSON.stringify(value), utf8, base64);
            encrypted += cipher.final(base64);
            return {
                data: encrypted,
                pass: await _encryptRSA(randomKey)
            }
        }catch(e){
            console.log(e);
            throw value;
        }
    }

    exports.validationSchema = async (payload, schema) => {
        let response = {
            isValid: false,
            payload: null,
            error: null
        }
        try{
            let stringDecrypted = await this.decrypt(payload.data, payload.pass);
            console.log(stringDecrypted)

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