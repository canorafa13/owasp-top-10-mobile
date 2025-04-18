(() => {
    'use strict';

    const Hapi = require('@hapi/hapi');
//    const https = require('node:https');
//    const http = require('node:http');
    const fs = require('node:fs');
    const prefix = "/owasp-demo/api/v1";

    const tls = {
        key: fs.readFileSync('private-key.pem', 'utf8'),
        cert: fs.readFileSync('localhost.cer.cer', 'utf8')
    };      
    
    const init = async () => {
        const server = Hapi.server({
            port: process.env.PORT,
            host: '0.0.0.0',
            tls: tls,
        });

        const swaggerOptions = {
            info: {
                title: 'Backend App OWASP Demo Documentation',
                version: "1.0.0"
            },
            basePath: prefix
        };

        await server.register([
            {
                plugin: require('@hapi/inert')
            }, {
                plugin: require("@hapi/vision")
            }, {
                plugin: require('hapi-swagger'),
                options: swaggerOptions
            }, {
                plugin: require('@hapi/jwt')
            }, {
                plugin: require('hapi-cors'),
                options: {
                    methods: ['POST', 'GET', 'PUT', 'DELETE']
                }
            }, {
                plugin: require('./plugins/users/index'),
                routes: {prefix}
            }, {
                plugin: require('./plugins/catalogs/index'),
                routes: {prefix}
            }
        ])


        await server.start();
        console.log('Server running on %s', server.info.uri);
    };
    
    process.on('unhandledRejection', (err) => {
        console.log(err);
        process.exit(1);
    });
    
    init();  
})();