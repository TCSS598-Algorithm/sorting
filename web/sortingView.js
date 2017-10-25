rand1000 = [191,41,190,69,14,194,10,200,189,207,56,41,204,219,219,202,114,141,118,217,239,210,115,26,171,189,78,5,172,115,131,78,116,161,2,148,86,39,174,66,234,7,159,197,115,238,11,41,56,246,163,78,152,53,73,159,32,51,78,56,149,103,65,153,247,59];

visualSort = (function () {
	var _container,
		_sortContainer = {},
		_updateQueue = {}, 
		_timeoutFlag = {},
		_timeLabel = {},

		_initView = function () {
			_container = $(document.body).find(".app");
		},
		
		_addSort = function (sortName) {
			_sortContainer[sortName] = $('<div class="sortContainer"/>');
			_timeLabel[sortName] = $('<span class="' + sortName.replace(/\s/g, "") + 'Time">');
			$('<h1 class="' + sortName.replace(/\s/g, "") + 'Header">').text(sortName).appendTo(_container);
			_timeLabel[sortName].text(0 + " ms").appendTo(_container);
			_sortContainer[sortName].appendTo(_container);
		},

		_getDataNode = function (value) {
			return $('<div class="dataNode" style="height:' + (value / 2.0) + 'px;"/>');
		},

		_update = function (sortName) {
			if (_timeoutFlag[sortName]) { clearTimeout(_timeoutFlag[sortName]); }
			_timeoutFlag[sortName] = setTimeout(function () {
				_timeoutFlag[sortName] = undefined;
				var update = _updateQueue[sortName].shift(),
					data = update.data;
				_sortContainer[sortName].empty();
				_timeLabel[sortName].text((update.time - update.start * 1.0)  + " ms");
				data.forEach(function (value) {
					_getDataNode(value).appendTo(_sortContainer[sortName]);
				});
				if (_updateQueue[sortName].length) {
					_update(sortName);
				}
			}, 1);
		}

		_updateView = function (data, sortName, startTime) {
			_updateQueue[sortName] = _updateQueue[sortName] || [];
			_updateQueue[sortName].push({data: data, start: startTime, time: Date.now()});
			_update(sortName);
		};

	return {
		sort: function (sortName, data, sortFunc) {
			_addSort(sortName);
			sortFunc.call(this, data, _updateView, sortName);
		},
		
		init: function () {
			_initView();
			visualSort.sort("Insert Sort", rand1000.slice(0), sorts.insertSort);
			visualSort.sort("Selection Sort", rand1000.slice(0), sorts.selectionSort);
			visualSort.sort("Bubble Sort", rand1000.slice(0), sorts.bubbleSort);
		}
	};
})();

sorts = (function () {
	return {		
		insertSort: function (data, updateFunc, sortName) {
			var startTime = Date.now(),
				i = 1;
			while (i < data.length) {
				var j = i;
				while ((j > 0) && (data[j-1] > data[j])) {
					var temp = data[j-1];
					data[j-1] = data[j];
					data[j] = temp;
					j--;
				}
				i++; 
				updateFunc.call(this, data.slice(0), sortName, startTime);
			}
		},
		
		selectionSort: function (data, updateFunc, sortName) {
			var startTime = Date.now();
			for (var i = 0; i < data.length - 1; i++) {
	            var index = i;
	            for (var j = i + 1; j < data.length; j++) {
	                if (data[j] < data[index]) 
	                    index = j;
	            }
	            var smaller = data[index];  
	            data[index] = data[i];
	            data[i] = smaller;
	            updateFunc.call(this, data.slice(0), sortName, startTime);
	        }
		},
		
		bubbleSort: function (data, updateFunc, sortName) {
			var startTime = Date.now();
			for (var i = 0; i < data.length; i++) {
				for (var j = i + 1; j < data.length; j++) {
					if (data[i] > data[j]) {
						var holder = data[i];
						data[i] = data[j];
						data[j] = holder;
					}
				}
				updateFunc.call(this, data.slice(0), sortName, startTime);
			}
		}
	};
})();