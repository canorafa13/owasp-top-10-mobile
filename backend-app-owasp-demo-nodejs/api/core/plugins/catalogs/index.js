(() => {
    'use strict';
    
    const catalogs = require('./catalogs');
    const handlers = require('./handlers');
    const middleware = require('../../utils/middleware');
    const session = require('../sessions/sessions');
    const baseSchema = require('../../schemas/baseSchema');
    const taskSchema = require('../../schemas/taskSchema');

    const plugin = {
        name: "catalogs",
        version: '1.0.0',
        register: async (server, options) => {
            server.route([{
                method: 'GET',
                path: '/roles',
                options: {
                    description: 'All roles',
                    notes: 'All roles',
                    tags: ['api', 'roles'],
                    pre: [
                        middleware.validateServer,
                        middleware.verifyToken
                    ],
                    handler: handlers.getAllRoles,
                    validate: {
                        headers: baseSchema.authorization,
                        options: {
                            allowUnknown: true
                        }
                    }
                }
            }, {
                method: 'POST',
                path: '/tasks',
                options: {
                    description: 'Crear Tarea',
                    notes: 'Crear tareas',
                    tags: ['api', 'crear', 'tasks'],
                    pre: [
                        middleware.validateServer,
                        middleware.verifyToken,
                        middleware.validationSchema(taskSchema.tasks)
                    ],
                    handler: handlers.createTasks,
                    validate: {
                        headers: baseSchema.authorization,
                        options: {
                            allowUnknown: true
                        },
                        payload: baseSchema.base
                    }
                }
            }, {
                method: 'PUT',
                path: '/tasks/{id}',
                options: {
                    description: 'Actualizar Tarea',
                    notes: 'Actualizar tareas',
                    tags: ['api', 'Actualizar', 'tasks'],
                    pre: [
                        middleware.validateServer,
                        middleware.verifyToken,
                        middleware.validationSchema(taskSchema.tasks)
                    ],
                    handler: handlers.updateTasks,
                    validate: {
                        headers: baseSchema.authorization,
                        options: {
                            allowUnknown: true
                        },
                        payload: baseSchema.base,
                        params: taskSchema.params.id
                    }
                }
            }, {
                method: 'DELETE',
                path: '/tasks/{id}',
                options: {
                    description: 'Eliminar Tarea',
                    notes: 'Eliminar tareas',
                    tags: ['api', 'eliminar', 'tasks'],
                    pre: [
                        middleware.validateServer,
                        middleware.verifyToken
                    ],
                    handler: handlers.deleteTasks,
                    validate: {
                        headers: baseSchema.authorization,
                        options: {
                            allowUnknown: true
                        },
                        params: taskSchema.params.id
                    }
                }
            }, {
                method: 'GET',
                path: '/tasks',
                options: {
                    description: 'Obtener Tareas por usuario',
                    notes: 'Obtener tareas por usuario',
                    tags: ['api', 'recuperar', 'tasks'],
                    pre: [
                        middleware.validateServer,
                        middleware.verifyToken
                    ],
                    handler: handlers.getTasks,
                    validate: {
                        headers: baseSchema.authorization,
                        options: {
                            allowUnknown: true
                        }
                    }
                }
            }, {
                method: 'POST',
                path: '/tasks/activities',
                options: {
                    description: 'Crear Actividad',
                    notes: 'Crear actividad',
                    tags: ['api', 'crear', 'actividad'],
                    pre: [
                        middleware.validateServer,
                        middleware.verifyToken,
                        middleware.validationSchema(taskSchema.activities)
                    ],
                    handler: handlers.createActivities,
                    validate: {
                        headers: baseSchema.authorization,
                        options: {
                            allowUnknown: true
                        },
                        payload: baseSchema.base
                    }
                }
            }, {
                method: 'PUT',
                path: '/tasks/activities/{id}',
                options: {
                    description: 'Actualizar Actividad',
                    notes: 'Actualizar Actividad',
                    tags: ['api', 'Actualizar', 'Actividad'],
                    pre: [
                        middleware.validateServer,
                        middleware.verifyToken,
                        middleware.validationSchema(taskSchema.activities)
                    ],
                    handler: handlers.updateActivities,
                    validate: {
                        headers: baseSchema.authorization,
                        options: {
                            allowUnknown: true
                        },
                        payload: baseSchema.base,
                        params: taskSchema.params.id
                    }
                }
            }, {
                method: 'DELETE',
                path: '/tasks/activities/{id}',
                options: {
                    description: 'Eliminar Actividad',
                    notes: 'Eliminar Actividad',
                    tags: ['api', 'eliminar', 'Actividad'],
                    pre: [
                        middleware.validateServer,
                        middleware.verifyToken
                    ],
                    handler: handlers.deleteActivities,
                    validate: {
                        headers: baseSchema.authorization,
                        options: {
                            allowUnknown: true
                        },
                        params: taskSchema.params.id
                    }
                }
            }, {
                method: 'GET',
                path: '/tasks/activities/{task_id}',
                options: {
                    description: 'Obtener Actividades por Tarea',
                    notes: 'Obtener Actividades por Tarea',
                    tags: ['api', 'recuperar', 'Tarea'],
                    pre: [
                        middleware.validateServer,
                        middleware.verifyToken
                    ],
                    handler: handlers.getActivities,
                    validate: {
                        headers: baseSchema.authorization,
                        options: {
                            allowUnknown: true
                        },
                        params: taskSchema.params.task_id
                    }
                }
            }]);

            
            server.method([{
                name: 'catalogs.getAllRoles',
                method: catalogs.getAllRoles
            }, {
                name: 'session.verify',
                method: session.verify
            }, {
                name: 'catalogs.createTasks',
                method: catalogs.createTasks
            }, {
                name: 'catalogs.updateTasks',
                method: catalogs.updateTasks
            }, {
                name: 'catalogs.deleteTasks',
                method: catalogs.deleteTasks
            }, {
                name: 'catalogs.getTasks',
                method: catalogs.getTasks
            }, {
                name: 'catalogs.createActivities',
                method: catalogs.createActivities
            }, {
                name: 'catalogs.updateActivities',
                method: catalogs.updateActivities
            }, {
                name: 'catalogs.deleteActivities',
                method: catalogs.deleteActivities
            }, {
                name: 'catalogs.getActivities',
                method: catalogs.getActivities
            }]);
        }
    }


    module.exports = plugin;
})();