(() => {
    'use strict';
    const cote = require('cote');

    const requester = new cote.Requester({
        name: 'Users Requester',
        key: 'users_key'
    });

    exports.signUp = async(payload) => {
        try{
            return await requester.send({type: 'signUp', payload});
        }catch(e){
            throw e;
        }
    }

    exports.signIn = async(username, password) => {
        try{
            return await requester.send({type: 'signIn', username, password});
        }catch(e){
            throw e;
        }
    }
})();