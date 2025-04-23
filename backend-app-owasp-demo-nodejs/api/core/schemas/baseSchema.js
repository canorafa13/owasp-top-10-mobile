(() => {
    'use strict';

    const Joi = require('joi');
    exports.base = Joi.object({
        data: Joi.string().required(),
        pass: Joi.string().required()
    })

    exports.apiKeyX = Joi.object({
        'api-key-x': Joi.string().required(),
    })

    exports.authorization = Joi.object({
        authorization: Joi.string().required()
    });
})();