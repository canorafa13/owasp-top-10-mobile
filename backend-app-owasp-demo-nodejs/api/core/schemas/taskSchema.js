(() => {
    'use strict';

    const Joi = require('joi');

    exports.tasks = Joi.object({
        username: Joi.string().required().max(40),
        title: Joi.string().required().max(50),
        description: Joi.string().required().max(200),
    });

    exports.activities = Joi.object({
        task_id: Joi.number().required(),
        description: Joi.string().required().max(200),
    });

    exports.params = {
        id: Joi.object({
            id: Joi.number().required()
        }),
        username: Joi.object({
            username: Joi.string().required().max(50)
        }),
        task_id: Joi.object({
            task_id: Joi.number().required()
        })
    }

})();