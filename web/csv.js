csv = (function () {
	var _lowMemory = false,
		_lookup,
		_rows,
		_cells,
		_parsedData,

		_reset = function () {
			_lookup = [];
			_rows = [];
			_cells = [];
			_parsedData = "";
		},

		_setLowMemory = function (flag) {
			_lowMemory = flag;
		},

		_rowSize = function () {
			return _cells.length;
		},

		_cellSize = function (rowIndex) {
			return _cells[rowIndex].length;
		},

	    _parseQuotes = function (csvString) {
	    	var index = 0;
	    	_parsedData = csvString.replace(/"[^"]*?"/g, function (match, group) {
    			_lookup[index] = match;
    			return _lookupValue(index++);
			});
	    },

	    _parseRows = function () {
	    	_rows = _parsedData.split(/[\n\r]/g).filter(function (value) { return !!value; });
	    	_parsedData = _lowMemory ? undefined : _parsedData;
	    },

	    _parseCells = function () {
	    	_rows.forEach(function(item, index) {
	    		_cells.push(item.split(",")/*.filter(function (value) { return !!value; })*/);
	    	});
	    	_rows = _lowMemory ? undefined : _rows;
	    },

	    _lookupValue = function (id) {
	    	return "%" + id + "%";
	    },

	    _cell = function (rowIndex, cellIndex) {
 	    	return _format(_cells[rowIndex][cellIndex]);
	    },

	    _headers = function () {
	    	var headers = _cells[0];
	    	headers.forEach(function (header, index) {
	    		headers[index] = _format(header);
	    	});
	    	return headers;
	    },

	    _columnIndex = function (headerName) {
	    	var index;
	    	_headers().forEach(function (header, i) {
	    		index = headerName === _format(header).replace(/"/g, "") ? i : index;
	    		//TODO: In forEach loops look how to early terminate, no time to test at the moment
	    	});
	    	return index;
	    },

		_columns = function (columnIndexArray) {
			var columns = [];
			_cells.forEach(function (row) {
				var tempRow = [];
				columnIndexArray.forEach(function (columnIndex) {
					tempRow.push(row[columnIndex]);
				});
				columns.push(tempRow);
			});
	    	return columns;
	    },

		_column = function (columnIndex, isFlatten) {
			var columns = [];
			_cells.forEach(function (row) {
				columns.push(isFlatten ? row[columnIndex] : [row[columnIndex]]);
			});
	    	return columns;
	    },

	    _getRows = function () {
	    	return _cells;
	    },

	    _toCsv = function (csvData, excludeHeader) {
	    	return csvData ? _formateCsv(csvData, excludeHeader) : _formateCsv(_cells, excludeHeader);
	    },

	    _formateCsv = function (csvData, excludeHeader) {
	    	var csvResult = "";
	    	csvData.forEach(function (string, index) {
	    		if (!(!!excludeHeader && index === 0)) {
		    		csvResult += Array.isArray(string) ? _formateCsv(string) + '\r\n' : _format(string) + ",";
		    	}
	    	});
	    	return csvResult.slice(0, -1);
	    },

	    _file = function (fileName, csvString) {
	    	var fileLink = _fileLink(fileName, csvString);
	    	_downloadFile(fileLink);
	    },

	    _fileLink = function (fileName, csvString) {
			fileName = fileName.replace(/ /g,"_");   
			uri = 'data:text/csv;charset=utf-8,' + escape(csvString);
			var fileLink = document.createElement("a");    
			fileLink.href = uri;
			fileLink.style = "visibility:hidden";
			fileLink.download = fileName + ".csv";
	    	return fileLink;
	    },

	    _downloadFile = function (fileLink) {
			document.body.appendChild(fileLink);
			fileLink.click();
			document.body.removeChild(fileLink);
	    },

	    _format = function (content) {
	    	return content ? content.replace(/%[0-9]*?%/g, function (match, group) {
	    		return _lookup[match.slice(1, -1)];
	    	}) : "";
	    };

	return {
		//TODO: Might want ot make accual datatype wrapper for csv data
		//TODO: Comment private functions
		//TODO: Add view

		/**
		 * Parse will clear any prior parsed data and will store start of the last parsed string
		 * NOTE: Must parse prior to any other requests
		 * NOTE: Data returned will have any quoted texted removed, must run through csv(<csvData>) to repopulate
		 * NOTE: csvData type is String[] or String[][]
		 * @param {string} csvString CSV format string
		 * @param {boolean} lowMemory When true will clean up during the parse process
		 */
	    parse: function (csvString, lowMemory) {
	    	_setLowMemory(!!lowMemory);
	    	_reset();
	    	_parseQuotes(csvString);
	    	_parseRows();
	    	_parseCells();
	    },

	    /**
	     * Returns a single colunm of data, can also return flattened
	     * @param {int} columnIndex Index of the column of data to return, 0 based indexed
	     * @param {boolean} isFlatten When true data will a string[] rather than string[][]
	     * @returns {csvData} string[] or string[][]
	     */
	    column: function (columnIndex, isFlatten) {
	    	return _column(columnIndex, isFlatten);
	    },


	    /**
	     * Returns a set of colunm of data
	     * @param {int[]} columnIndexArray Array of indexs of the columns of data to return, 0 based indexed
	     * @returns {csvData} string[][]
	     */
	    columns: function (columnIndexArray) {
	    	return _columns(columnIndexArray);
	    },

	    rows: function () {
	    	return _getRows();
	    },

	    format: function (content) {
	    	return _format(content);
	    },

	    /**
	     * Returns headers
	     * @returns {csvData} string[]
	     */
	    headers: function () {
	    	return _headers();
	    },

	    /**
	     * Returns the column number based on header name
	     * @param {string} headerName Nameo of header you want column index for
	     * @return {int} index of the column with matching header name
	     */
	    columnIndex: function (headerName) {
	    	return _columnIndex(headerName);
	    },

	    /**
	     * Will take csvData (string[] or string [][]) and do string replacements for data stripped during 
	     * the parse and will output it back as a csv string
	     * Note: csvData must be created after parse of the origonal csvString
	     * @param {csvData} csvData Date to convert to csv string must be string[] or string[][]
	     * @param {boolean} excludeHeader Will strip the first row out of the data
	     * @return {string} Value will be in csv format
	     */
	    csv: function (csvData, excludeHeader) {
	    	return _toCsv(csvData, excludeHeader);
	    },

	    /**
	     * Takes csv content and saves as a csv file
	     * @param {string} fileName Name of file spaces will be replaced with _
	     * @param {string} csvString Content of csv file
	     */
	    file: function (fileName, csvString) {
	    	_file(fileName, csvString);
	    }
	}
})();