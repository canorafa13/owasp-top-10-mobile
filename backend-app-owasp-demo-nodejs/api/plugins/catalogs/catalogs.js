(() => {
    'use strict';
    const cote = require('cote');

    const requester = new cote.Requester({
        name: 'Catalogs Requester',
        key: 'catalogs_key'
    });

    exports.getAllRoles = async() => {
        try{
            return await requester.send({type: 'getAllRoles'});
        }catch(e){
            throw e;
        }
    }
})();