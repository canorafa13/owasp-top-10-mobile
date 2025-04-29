(() => {
    'use strict';
    const Boom = require('@hapi/boom');
    const cryptii = require('../../utils/cryptii');
    const baseResponse = require('../../schemas/baseResponse');

    exports.getAllRoles = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const roles = await req.server.methods.catalogs.getAllRoles();
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(roles));
            }
            return baseResponse.success(roles);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

    /// Tasks
    exports.createTasks = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const methods = req.server.methods;
            const payload = req.pre.payload || req.payload;

            if(isSecure){
                payload.username = req.pre.resultAuthorization.username;
            }

            const result = await methods.catalogs.createTasks(payload);
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(result));
            }
            return baseResponse.success(result);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

    exports.updateTasks = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const methods = req.server.methods;
            const payload = req.pre.payload || req.payload;
            const id = req.params.id;

            if(isSecure){
                payload.username = req.pre.resultAuthorization.username;
            }


            const result = await methods.catalogs.updateTasks(id, payload);
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(result));
            }
            return baseResponse.success(result);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

    exports.deleteTasks = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const methods = req.server.methods;
            const id = req.params.id;


            const result = await methods.catalogs.deleteTasks(id);
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(result));
            }
            return baseResponse.success(result);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

    exports.getTasks = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const methods = req.server.methods;
            let username = isSecure ? req.pre.resultAuthorization.username : req.params.username;


            const result = await methods.catalogs.getTasks(username);
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(result));
            }
            return baseResponse.success(result);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }


    /// Activities
    exports.createActivities = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const methods = req.server.methods;
            const payload = req.pre.payload || req.payload;


            const result = await methods.catalogs.createActivities(payload);
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(result));
            }
            return baseResponse.success(result);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

    exports.updateActivities = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const methods = req.server.methods;
            const payload = req.pre.payload || req.payload;
            const id = req.params.id;


            const result = await methods.catalogs.updateActivities(id, payload);
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(result));
            }
            return baseResponse.success(result);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

    exports.deleteActivities = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const methods = req.server.methods;
            const id = req.params.id;


            const result = await methods.catalogs.deleteActivities(id);
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(result));
            }
            return baseResponse.success(result);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

    exports.getActivities = async(req, h) => {
        try{
            const isSecure = req.pre.isSecure || false;
            const methods = req.server.methods;
            const task_id = req.params.task_id;


            const result = await methods.catalogs.getActivities(task_id);
            if(isSecure){
                return baseResponse.success(await cryptii.encrypt(result));
            }
            return baseResponse.success(result);
        }catch(e){
            throw Boom.notImplemented(JSON.stringify(e));
        }
    }

})();