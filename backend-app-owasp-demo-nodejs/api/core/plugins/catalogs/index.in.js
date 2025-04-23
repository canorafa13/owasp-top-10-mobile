(() => {
    'use strict';
    
    const catalogs = require('./catalogs');
    const handlers = require('./handlers');
    const taskSchema = require('../../schemas/taskSchema');

    const plugin = {
        name: "catalogs",
        version: '1.0.0',
        register: async (server, options) => {
            server.route([{
                method: 'GET',
                path: '/roles',
                handler: handlers.getAllRoles,
                options: {
                    description: 'All roles',
                    notes: 'All roles',
                    tags: ['api', 'roles']
                }
            }, {
                method: 'POST',
                path: '/tasks',
                options: {
                    description: 'Crear Tarea',
                    notes: 'Crear tareas',
                    tags: ['api', 'crear', 'tasks'],
                    handler: handlers.createTasks,
                    validate: {
                        payload: taskSchema.tasks
                    }
                }
            }, {
                method: 'PUT',
                path: '/tasks/{id}',
                options: {
                    description: 'Actualizar Tarea',
                    notes: 'Actualizar tareas',
                    tags: ['api', 'Actualizar', 'tasks'],
                    handler: handlers.updateTasks,
                    validate: {
                        payload: taskSchema.tasks,
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
                    handler: handlers.deleteTasks,
                    validate: {
                        params: taskSchema.params.id
                    }
                }
            }, {
                method: 'GET',
                path: '/tasks/{username}',
                options: {
                    description: 'Obtener Tareas por usuario',
                    notes: 'Obtener tareas por usuario',
                    tags: ['api', 'recuperar', 'tasks'],
                    handler: handlers.getTasks,
                    validate: {
                        params: taskSchema.params.username
                    }
                }
            }, {
                method: 'POST',
                path: '/tasks/activities',
                options: {
                    description: 'Crear Actividad',
                    notes: 'Crear actividad',
                    tags: ['api', 'crear', 'actividad'],
                    handler: handlers.createActivities,
                    validate: {
                        payload: taskSchema.activities
                    }
                }
            }, {
                method: 'PUT',
                path: '/tasks/activities/{id}',
                options: {
                    description: 'Actualizar Actividad',
                    notes: 'Actualizar Actividad',
                    tags: ['api', 'Actualizar', 'Actividad'],
                    handler: handlers.updateActivities,
                    validate: {
                        payload: taskSchema.activities,
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
                    handler: handlers.deleteActivities,
                    validate: {
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
                    handler: handlers.getActivities,
                    validate: {
                        params: taskSchema.params.task_id
                    }
                }
            }]);


            server.method([{
                name: 'catalogs.getAllRoles',
                method: catalogs.getAllRoles
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