var App = App || {};

App.utils = App.utils || {};

App.utils.createGrid = function(config) {
	if (!config) {
		throw new error("Cannot initialize the grid", config)
	}
	var dataUrl = config.dataUrl,
		columns = config.columns,
		target = config.target || 'body',
		data,
		generateRow = function(item) {
			var tr = jQuery('<tr>');
			columns.forEach(function(column) {
				var td = jQuery('<td>');
				td.text(item[column]);
				tr.append(td);
			});
			return tr;
		},
		generateHeader = function() {
			var thead = jQuery('<thead>'),
				tr = jQuery('<tr>');
			columns.forEach(function(column) {
				var th = jQuery('<th>');
				th.text(column);
				tr.append(th);
			})
			thead.append(tr);
			return thead;
		},
		generateBody = function() {
			var tbody = $('<tbody>');
			data.forEach(function(item) {
				var row;
				row = generateRow(item);
				tbody.append(row);
			});
			return tbody;
		},
		generateTable = function(){
			var table = $('<table>');
			table.append(generateHeader());
			table.append(generateBody());
			table.addClass('table')
			return table;
		},
		buildGrid = function() {
			var table = generateTable();
			$(target).append(table);
		};

	jQuery.ajax({
		method: "GET",
		url: "http://hexedware.com:8080/user/list",
		success: function(resp) {
			data = resp.users;
			buildGrid();
		},
		error: function(e) {
			console.log(e)
		}
	})

};