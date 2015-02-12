(function() {
	var app = angular.module('myApp', [ 'app-query', 
	                                    'ngMap', 
	                                    'readableTime' ]);
	app
			.controller(
					'mapController',
					function($scope, $http, $interval, $filter) {
						
						$scope.date = Date();
						
						$scope.generateMap = function(query) {
							var markers = new Object();
							var infoWindows = new Object();
							
							if (isEmpty(query)) {
								query = "House"
							}
							
							query = encodeURIComponent(query);
//							alert(query);

							// Initialize Markers & infoWindows
							// Max 20 items
							for (var i = 0; i < 20; i++) {
								markers[i] = new google.maps.Marker({
									title : i.toString()
								})
								markers[i].setVisible(false);

								infoWindows[i] = new google.maps.InfoWindow({
									content : ""
								})
							}
							
							tracks = [];

							// http://46.182.19.192:8080/AngularJSTestWebserviceJDK6/GetLatestTracks?query=House
							$http
									.get(
											'GetLatestTracks?query=' + query)
									.success(
											function(data) {
												tracks = data;

												for (i = 0; i < tracks.length; i++) {

													var latlng = new google.maps.LatLng(
															tracks[i].lat,
															tracks[i].lng);
													markers[i].setPosition(latlng);
													markers[i].setMap($scope.map);

													var currentTitle = getTitleString(tracks[i]);
													markers[i]
															.setTitle(currentTitle);
													

													infoWindows[i]
															.setPosition(latlng);
													infoWindows[i]
															.setContent(getInfoWindowHtml(tracks[i]));
													infoWindows[i].open($scope.map);
													

												}
											});
							
						}

						function getTitleString(currentTrack) {
							var currentTitle = currentTrack.username + " - "
							+ currentTrack.title;
							return currentTitle;
						}
						
						function getInfoWindowHtml(currentTrack) {
							// Milliseconds to readableTime-filter
							var duration = $filter('readableTime')(currentTrack.duration / 1000);
							
							var description;
							if(isEmpty(currentTrack.description)) {
								description = "";
							} else { 
								description = $filter('limitTo')(currentTrack.description, 200) + "...";
							}
							var currentHtml = "<h3>" +
									"<a target='soundcloud' href='" 
								+ currentTrack.permalink_url 
								+ "'>"
								+ getTitleString(currentTrack) + "</a></h3>";
							currentHtml = currentHtml + "<div><ul>"
							+ "<li><b>duration:</b> " + duration
							+ " | <b>created_at:</b> "
							+ currentTrack.created_at + "</li>"
							+ "<li><b>Genre:</b> " + currentTrack.genre 
							+ " | <b>PlayCount:</b> " + currentTrack.playback_count
							+	"</li>"
							+ "</ul>" +  description
							+ "</div>"
							
							return currentHtml;
						}
						
						function isEmpty(obj) {
						    for(var prop in obj) {
						        if(obj.hasOwnProperty(prop))
						            return false;
						    }
						    return true;
						}
						
					});

})();