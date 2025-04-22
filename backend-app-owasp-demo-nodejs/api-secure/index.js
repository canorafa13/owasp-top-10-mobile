(() => {
    'use strict';

    const Hapi = require('@hapi/hapi');
    const fs = require('node:fs');


    const prefixSecure = "/secure/owasp-demo/api/v1";
    const prefixInsecure = "/insecure/owasp-demo/api/v1";

    const tls = {
        key: fs.readFileSync('./cert/private-key.pem', 'utf8'),
        cert: fs.readFileSync('./cert/localhost.cer.cer', 'utf8')
    };      
    
    const init = async () => {
        const serverSecure = Hapi.server({
            port: process.env.PORT_HTTPS,
            host: '0.0.0.0',
            tls: tls,
        });

        const serverInsecure = Hapi.server({
            port: process.env.PORT_HTTP,
            host: '0.0.0.0'
        });

        const swaggerOptions = (tag, prefix) => {
            return {
                info: {
                    title: 'Backend App OWASP Demo Documentation ' + tag,
                    version: "1.0.0"
                },
                basePath: prefix
            }
        }

        const basePlugins = (tag, suf, prefix) => {
            return [
                {
                    plugin: require('@hapi/inert')
                }, {
                    plugin: require("@hapi/vision")
                }, {
                    plugin: require('hapi-swagger'),
                    options: swaggerOptions(tag, prefix)
                },  {
                    plugin: require('hapi-cors'),
                    options: {
                        methods: ['POST', 'GET', 'PUT', 'DELETE']
                    }
                }, {
                    plugin: require('./core/plugins/users/index' + suf),
                    routes: {prefix}
                }, {
                    plugin: require('./core/plugins/catalogs/index' + suf),
                    routes: {prefix}
                }
            ];
        }

        /*const swaggerOptions = {
            info: {
                title: 'Backend App OWASP Demo Documentation Secure',
                version: "1.0.0"
            },
            basePath: prefix
        };*/

        const registerSecure = basePlugins("Secure", "", prefixSecure);

        const registerInsecure = basePlugins("Insecure", '.in', prefixInsecure);

        await serverSecure.register(registerSecure);

        await serverInsecure.register(registerInsecure);
        

        /*await serverSecure.register([
            {
                plugin: require('@hapi/inert')
            }, {
                plugin: require("@hapi/vision")
            }, {
                plugin: require('hapi-swagger'),
                options: swaggerOptions
            }, {
                plugin: require('hapi-cors'),
                options: {
                    methods: ['POST', 'GET', 'PUT', 'DELETE']
                }
            }, {
                plugin: require('./core/plugins/users/index'),
                routes: {prefix}
            }, {
                plugin: require('./core/plugins/catalogs/index'),
                routes: {prefix}
            }
        ]);*/

        await serverSecure.start();
        await serverInsecure.start();
        console.log('Server secure running on %s', serverSecure.info.uri);
        console.log('Server insecure running on %s', serverInsecure.info.uri);
    };
    
    process.on('unhandledRejection', (err) => {
        console.log(err);
        process.exit(1);
    });
    
    init();  
})();