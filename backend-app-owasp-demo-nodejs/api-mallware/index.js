(() => {
    'use strict';

    const Hapi = require('@hapi/hapi');

    const prefix = "/mallware/owasp-demo/api/v1";

    const init = async () => {
       

        const server = Hapi.server({
            port: process.env.PORT_MALLWARE,
            host: '0.0.0.0'
        });

      
        await server.register([
            {
                plugin: require('@hapi/inert')
            }, {
                plugin: require("@hapi/vision")
            }, {
                plugin: require('hapi-swagger'),
                options: {
                    info: {
                        title: 'Backend App OWASP Demo Documentation Mallware',
                        version: "1.0.0"
                    },
                    basePath: prefix
                }
            },  {
                plugin: require('hapi-cors'),
                options: {
                    methods: ['POST', 'GET', 'PUT', 'DELETE']
                }
            }, {
                plugin: require('./plugins/records/index'),
                routes: {prefix}
            }
        ]);

        await server.start();
        console.log('Server mallware running on %s', server.info.uri);
    };
    
    process.on('unhandledRejection', (err) => {
        console.log(err);
        process.exit(1);
    });
    
    init();  
})();