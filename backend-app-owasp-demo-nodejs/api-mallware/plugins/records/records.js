(() => {
    'use strict';
    const cote = require('cote');

    const requester = new cote.Requester({
        name: 'Records Requester',
        key: 'records_key'
    });

    exports.create = async(payload) => {
        try{
            return await requester.send({type: 'create', payload});
        }catch(e){
            throw e;
        }
    }
})();