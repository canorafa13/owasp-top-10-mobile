(() => {
    'use strict';

    const cote = require('cote');
    const repository = require('./repository');

    const responder = new cote.Responder({
        name: 'Users Responder',
        key: 'users_key'
    });

    responder.on('signUp', async (req) => await repository.signUp(req.payload));
    responder.on('signIn', async (req) => {
        const user = await repository.signIn(req.username, req.password);
        if(user.length > 0){
            if(user[0].status === 'ACTIVE'){
                return user[0];
            }else{
                return 'Usuario inactivo';
            }
        }else{
            return 'Contraseña o usuario incorrecto';
        }
    });
})();