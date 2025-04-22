(() => {
    'use strict';

    const cote = require('cote');
    const repository = require('./repository');

    const responder = new cote.Responder({
        name: 'Catalogs Responder',
        key: 'catalogs_key'
    });

    responder.on('getAllRoles', async () => await repository.getAllRoles());

    responder.on('createTasks', async(req) => await repository.createTasks(req.payload) );
    responder.on('updateTasks', async(req) => await repository.updateTasks(req.id, req.payload) );
    responder.on('deleteTasks', async(req) => await repository.deleteTasks(req.id) );
    responder.on('getTasks', async(req) => await repository.getTasks(req.username) );


    responder.on('createActivities', async(req) => await repository.createActivities(req.payload) );
    responder.on('updateActivities', async(req) => await repository.updateActivities(req.id, req.payload) );
    responder.on('deleteActivities', async(req) => await repository.deleteActivities(req.id) );
    responder.on('getActivities', async(req) => await repository.getActivities(req.task_id) );
})();