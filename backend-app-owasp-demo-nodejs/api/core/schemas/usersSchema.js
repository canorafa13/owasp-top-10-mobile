(() => {
    'use strict';

    const Joi = require('joi');
    exports.signIn = Joi.object({
        username: Joi.string().required().max(40),
        password: Joi.string().required().max(40),
    })

    exports.signUp = Joi.object({
        username: Joi.string().required().max(40),
        password: Joi.string().required().max(40),
        name: Joi.string().required().max(100),
        last_name: Joi.string().required().max(150),
        phone: Joi.string().max(12).allow(null)
    })
})();