(() => {
    'use strict';
    const Boom = require('@hapi/boom');

    exports.records = async(req, h) => {
        try{
            const methods = req.server.methods;
            const payload = req.payload;

            const result = await methods.records.create(payload);
            
            return {
                statusCode: 200,
                error: null,
                message: "Success",
                data: result
            };
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

})();