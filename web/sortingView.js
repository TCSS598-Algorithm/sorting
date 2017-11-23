//data used for testing not used when csv file is uploaded. 
_data = [191,41,190,69,14,194,10,200,189,207,56,41,204,219,219,202,114,141,118,217,239,210,115,26,171,189,78,5,172,115,131,78,116,161,2,148,86,39,174,66,234,7,159,197,115,238,11,41,56,246,163,78,152,53,73,159,32,51,78,56,149,103,65,153,247,59,191,5,172,115,131,78,116,161,2,148,86,39,174,66,234,7,159,197,115,238,11,41,56,246,163,78,152,53,73,159,32,51,78,56,149,103,65,153,247,59,191,41,190,69,14,194,10,200,189,207,56,41];
data = []; 
scale = .013;

visualSort = (function () {
    var _container,
        _sortContainer = {},
        _updateQueue = {},
        _timeoutFlag = {},

        _initView = function () {
            _container = $(document.body).find(".app");
        },

        _addSort = function (sortName) {
            _sortContainer[sortName] = $('<div class="sortContainer"/>');
            $('<h1 class="' + sortName.replace(/\s/g, "") + 'Header">').text(sortName).appendTo(_container);
            _sortContainer[sortName].appendTo(_container);
        },

        _getDataNode = function (value) {
            return $('<div class="dataNode" style="height:' + ((value / 2.0) * scale) + 'px;"/>');
        },

        _update = function (sortName) {
            if (_timeoutFlag[sortName]) { clearTimeout(_timeoutFlag[sortName]); }
            _timeoutFlag[sortName] = setTimeout(function () {
                _timeoutFlag[sortName] = undefined;
                var speed = Math.ceil(_updateQueue[sortName].length / 500),
                	update = function () {
                		var value;
                		for (i = 0; i < speed; i++) {
                			value = _updateQueue[sortName].shift();
                		}
                		return value;
                	}.call();
                    data = update.data;
                _sortContainer[sortName].empty();
                data.forEach(function (value) {
                    _getDataNode(value).appendTo(_sortContainer[sortName]);
                });
                if (_updateQueue[sortName].length) {
                    _update(sortName);
                } else {
                	$(document.body).trigger("completed");
                }
            }, 0);
        },

        _updateView = function (data, sortName) {
            _updateQueue[sortName] = _updateQueue[sortName] || [];
            _updateQueue[sortName].push({data: data});
            _update(sortName);
        };

    return {
        sort: function (sortName, data, sortFunc) {
        	_container.empty();
        	var deferred = $.Deferred();
            _addSort(sortName);
            sortFunc.call(this, data, _updateView, sortName);
            $(document.body).on("completed", function () { deferred.resolve(); });
            return deferred.promise();
        },
        
        setup: function () {
        	$("input[type=file]").on("change", $.proxy(this.loadCsv, this));
        },

        init: function () {
            _initView();

            visualSort.sort("Insertion Sort", data = _data.slice(0), sorts.insertSort).then( function () {
        		visualSort.sort("Selection Sort", data = _data.slice(0), sorts.selectionSort).then( function () {
    				visualSort.sort("Bubble Sort", data = _data.slice(0), sorts.bubbleSort).then( function () {
						visualSort.sort("Merge Sort", data = _data.slice(0), sorts.mergeSort).then( function () {
							visualSort.sort("Quick Sort", data = _data.slice(0), sorts.quickSort);
            			});
            		});	
            	});	
            });
        },
        
        loadCsv: function (evt) {
        	var colLookup = {
        		"BostonDeaths.csv": {col: 7, scale: 2},
        		"ReverselandDeaths.csv": {col: 7, scale: .013},
        		"SortinglandDeaths.csv": {col: 7, scale: .013},
        		"TacomaDeaths.csv": {col: 7, scale: 2},
        		"WashDeath.csv": {col: 7, scale: 2}
        	};
        	var scope = this;
        	var file = evt.target.files[0];
        	var reader = new FileReader();
        	var metaData = colLookup[file.name];
        	$(reader).on("load", function (e) {
        		csv.parse(e.target.result, true);
        		var col = colLookup[e.fileName];
        		_data = JSON.parse("[" + csv.csv(csv.column(metaData.col, true), false) + "]");
        		scale = metaData.scale;
        		scope.init();
        	});
        	reader.readAsText(file,"UTF-8");
        }
    };
})();

sorts = (function () {
	//merge sort
    var _divideAndConquer = function (data, updateFunc, sortName) {
        var result = data,
            divide = Math.floor(data.length / 2);
            if (divide) {
                result = _conquer(
                            _divideAndConquer(data.slice(0, divide), updateFunc, sortName),
                            _divideAndConquer(data.slice(divide, data.length), updateFunc, sortName),
                            updateFunc,
                            sortName
                        );
            }
            return result;
        },

        _conquer = function (dataLeft, dataRight, updateFunc, sortName) {
            var result = [],
                  lIndex = 0,
                  rIndex = 0;

            while (lIndex < dataLeft.length || rIndex < dataRight.length) {
                if (lIndex >= dataLeft.length) {
                    result.push(dataRight[rIndex++]);
                } else if (rIndex >= dataRight.length) {
                    result.push(dataLeft[lIndex++]);
                } else {
                      result.push(dataLeft[lIndex] < dataRight[rIndex] ? dataLeft[lIndex++] : dataRight[rIndex++]);
                }
                updateFunc.call(this, result.slice(0).concat(dataLeft.slice(lIndex)).concat(dataRight.slice(rIndex)), sortName); //where each snapshot of visualization is done.
            }
            return result;
        },
     //quick sort
    	_quickSort = function(data, left, right, updateFunc, sortName) {
        	var pivot,
        	    partitionIndex; 
        	
    		if (left < right) {
    			pivot = right; 
    			var partitionIndex = _partition(data, pivot, left, right, updateFunc, sortName); // part the red sea (so to speak)
    			
    			//sort left and right 
    			_quickSort(data, left, partitionIndex - 1, updateFunc, sortName);
    			_quickSort(data, partitionIndex + 1, right, updateFunc, sortName);
    		}
    	},

    	_partition = function(data, pivot, left, right, updateFunc, sortName) {
    		  var pivotValue = data[pivot],
    		      partitionIndex = left;
    		  
    		  for(var i = left; i < right; i++){
    			  if(data[i] < pivotValue){
    				  _swap(data, i, partitionIndex, updateFunc, sortName);
    				  partitionIndex++;
    				  }
    			  }
    		  _swap(data, right, partitionIndex, updateFunc, sortName);
    		  return partitionIndex;
    	},
    	
    	_swap = function(data, i, j, updateFunc, sortName) {
    		var temp = data[i];
    		data[i] = data[j];
    		data[j] = temp;
    		updateFunc.call(this, data.slice(0), sortName); //where each snapshot of visualization is done.
    	};
    	//sort function/visuals     	
    	return {
        insertSort: function (data, updateFunc, sortName) {
            var i = 1;
            while (i < data.length) {
                var j = i;
                while ((j > 0) && (data[j-1] > data[j])) {
                    var temp = data[j-1];
                    data[j-1] = data[j];
                    data[j] = temp;
                    j--;
                }
                i++;
                updateFunc.call(this, data.slice(0), sortName); //where each snapshot of visualization is done.
            }
        },

        selectionSort: function (data, updateFunc, sortName) {
            for (var i = 0; i < data.length - 1; i++) {
                var index = i;
                for (var j = i + 1; j < data.length; j++) {
                    if (data[j] < data[index])
                        index = j;
                }
                var smaller = data[index];
                data[index] = data[i];
                data[i] = smaller;
                updateFunc.call(this, data.slice(0), sortName); //where each snapshot of visualization is done.
            }
        },

        bubbleSort: function (data, updateFunc, sortName) {
            for (var i = 0; i < data.length; i++) {
                for (var j = i + 1; j < data.length; j++) {
                    if (data[i] > data[j]) {
                        var holder = data[i];
                        data[i] = data[j];
                        data[j] = holder;
                    }
                }
                updateFunc.call(this, data.slice(0), sortName); //where each snapshot of visualization is done.
            }
        },

        mergeSort: function (data, updateFunc, sortName) {
            _divideAndConquer(data, updateFunc, sortName);
        },
        

        quickSort: function (data, updateFunc, sortName) {
            _quickSort(data, 0, data.length-1, updateFunc, sortName);
        }    
    };
})();