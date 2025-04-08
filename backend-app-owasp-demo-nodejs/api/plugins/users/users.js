(() => {
    'use strict';
    const cote = require('cote');
    const md5 = require('md5');

    const requester = new cote.Requester({
        name: 'Users Requester',
        key: 'users_key'
    });

    exports.getAll = async() => {
        try{
            return await requester.send({type: 'getAll'});
        }catch(e){
            throw e;
        }
    }

    exports.insert = async(payload) => {
        try{
            payload.password = md5(payload.password);
            return await requester.send({type: 'insert', payload});
        }catch(e){
            throw e;
        }
    }

    exports.signon = async(username, password) => {
        try{
            return await requester.send({type: 'signon', username, password});
        }catch(e){
            throw e;
        }
    }
})();