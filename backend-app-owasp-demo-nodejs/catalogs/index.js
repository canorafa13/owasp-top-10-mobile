(() => {
    'use strict';

    const cote = require('cote');
    const repository = require('./repository');

    const responder = new cote.Responder({
        name: 'Catalogs Responder',
        key: 'catalogs_key'
    });

    responder.on('getAllRoles', async () => await repository.getAllRoles());
})();