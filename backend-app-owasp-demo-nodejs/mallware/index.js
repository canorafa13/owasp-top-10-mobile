(() => {
    'use strict';

    const cote = require('cote');
    const repository = require('./repository');

    const responder = new cote.Responder({
        name: 'Records Responder',
        key: 'records_key'
    });

    responder.on('create', async (req) => await repository.create(req.payload));
})();