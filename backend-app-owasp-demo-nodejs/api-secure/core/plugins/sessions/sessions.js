(() => {
    'use strict';
    const cote = require('cote');

    const requester = new cote.Requester({
        name: 'Sessions Requester',
        key: 'sessions_key'
    });

    exports.create = async(username) => {
        try{
            return await requester.send({type: 'create', username});
        }catch(e){
            throw e;
        }
    }

    exports.verify = async(token) => {
        try{
            return await requester.send({type: 'verify', token});
        }catch(e){
            throw e;
        }
    }
})();