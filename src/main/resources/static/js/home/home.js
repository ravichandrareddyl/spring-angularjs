var HomeComponent = ng.core.Component({
    templateUrl : 'js/home/home.html'
}).Class({
    constructor : [AppService, ng.http.Http, function(app, http) {
        var self = this;
        this.homeModel = {id:'', msg:'', visible : false, visibleAnimate : false};
        
        self.visible = false;
        self.visibleAnimate = false;
        
        self.show = function () {
        	self.visible = true;
        	setTimeout(() => self.visibleAnimate = true, 100);
        }
        
        self.hide = function () {
        	self.visibleAnimate = false;
        	setTimeout(() => self.visible = false, 100);
        }
        
        self.onContainerClicked = function (event) {
        	if ((event.target).classList.contains('modal')) {
        	      self.hide();
        	    }
        }
        
        this.uploadedFile = function (event) {
        	let fileList = event.target.files;
        	if (fileList.length > 0) {
        		let file = fileList[0];
        	    let formData = new FormData();
        		formData.append('uploadfile', file, file.name);
        		http.post('csvupload', formData)
        		    //.map(res: Response)
        			.subscribe(response => {
        				console.log('sucess');
        				event.target.value = null;
        				self.hide();
        			}, error => {
        				console.log('error');
        				event.target.value = null;
        				self.hide();
        			})
        	}
        };
        
        http.post('damProfiles').subscribe(response => self.homeModel.damFiles = response.json());
        this.authenticated = function() { return app.authenticated; };
    }]
});
