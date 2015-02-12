(function() {
	var app = angular.module('app-query', [ ]);
	
	app.directive('queryForm', function(){
		return{
			restrict: 'E',
			templateUrl: 'query-form.html',
				
			controller: function() {
				
			}
			
		}
	});
	
	app.controller('QueryController', function() {
		this.query = {};
		
		this.sendQuery = function(query) {
			alert(encodeURIComponent(query));
		}
	});
})();