const updateData = (table, query) => {
    let keys = []
    if (table == "user") keys = ["id", "login"]
    else if (table == "authority") keys = ["id", "role", "res"]
    else if (table == "activity") keys = ["id", "ds", "de", "vol"]

    let req = new Request("ajax/" + table + "?" + query)
    fetch(req).then(response => response.json()).then(data => {
        ReactDOM.render(
            React.createElement(Table, { "data": data, "table": table, "keys": keys, "query": query }, null),
            document.getElementById("table_container")
        );
    })
}
