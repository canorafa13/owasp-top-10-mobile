(() => {
    'use strict';
    
    exports.success = (data) => {
        return {
            statusCode: 200,
            error: null,
            message: "Success",
            data
        };
    }
})();