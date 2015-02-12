(function() {
	var app = angular.module('myApp', ['ngMap']);
	app.controller('mapController',
				function($scope, $http, $interval) {
					// $scope.map .. this exists after the map is initialized
					var markers = [];
					for (var i = 0; i < 8; i++) {
						markers[i] = new google.maps.Marker({
							title : i.toString()
						})
					}

					$scope.GenerateMapMarkers = function() {
						$scope.date = Date(); // Just to show that we are
												// updating

						var spikes = this;
						spikes.tracks = [];
						spikes.lat = 0;
						spikes.lng = 0;
						
						$http.get('//127.0.0.1:8090/AngularJSTestWebservice/GetLatestTracks')
								.success(function(data) {
									spikes.tracks = data;
									
									for (i = 0; i < tracks.length; i++) {
										
										var latlng = new google.maps.LatLng(tracks[i].lat, tracks[i].lng);
										markers[i].setPosition(latlng);
										markers[i].setMap($scope.map);
										var title = tracks[i].username + " - " + tracks[i].title;
										markers[i].title =  title ;
									}
								});


					};

					$interval($scope.GenerateMapMarkers, 2000);

				}); // mapController

})();