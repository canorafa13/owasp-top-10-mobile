(() => {
    'use strict';

    const cote = require('cote');
    const repository = require('./repository');

    const responder = new cote.Responder({
        name: 'Users Responder',
        key: 'users_key'
    });

    responder.on('getAll', async () => await repository.getAll());
    responder.on('insert', async (req) => await repository.insert(req.payload));
    responder.on('signon', async (req) => {
        const user = await repository.signon(req.username, req.password);
        if(user.length > 0){
            if(user[0].status === 'ACTIVE'){
                return user[0];
            }else{
                return 'Inactive user';
            }
        }else{
            return 'Invalid user or password';
        }
    });
})();