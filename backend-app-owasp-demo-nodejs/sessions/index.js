(() => {
    'use strict';
    const cote = require('cote');
    const repository = require('./repository');

    const responder = new cote.Responder({
        name: 'Sessions Responder',
        key: 'sessions_key'
    });
    
    responder.on('create', async (req) => await repository.create(req.username));
    responder.on('verify', async (req) => await repository.verify(req.token));

})();