(() => {
    'use strict';
    const cote = require('cote');

    const requester = new cote.Requester({
        name: 'Catalogs Requester',
        key: 'catalogs_key'
    });

    const _requesterSend = async (object) => {
        try{
            return await requester.send(object);
        }catch(e){
            throw e;
        }
    }


    exports.getAllRoles = async () => await _requesterSend({type: 'getAllRoles'});

    /// Tasks
    exports.createTasks = async (payload) => await _requesterSend({type: 'createTasks', payload});
    exports.updateTasks = async (id, payload) => await _requesterSend({type: 'updateTasks', id, payload});
    exports.deleteTasks = async (id) => await _requesterSend({type: 'deleteTasks', id});
    exports.getTasks = async (username) => await _requesterSend({type: 'getTasks', username});


    /// Activities
    exports.createActivities = async (payload) => await _requesterSend({type: 'createActivities', payload});
    exports.updateActivities = async (id, payload) => await _requesterSend({type: 'updateActivities', id, payload});
    exports.deleteActivities = async (id) => await _requesterSend({type: 'deleteActivities', id});
    exports.getActivities = async (task_id) => await _requesterSend({type: 'getActivities', task_id});
    

})();