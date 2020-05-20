import React from "react"
import ReactDOM from "react-dom"
import Form from "./Form.js"
import Table from "./Table.js"

const handler = (table, id) => {
    if (table == "user") updateData("authority", `userId=${id}`)
    else if (table == "authority") updateData("activity", `authorityId=${id}`)
}

const updateData = (table, query) => {
    let keys = []
    if (table == "user") keys = ["id", "login"]
    else if (table == "authority") keys = ["id", "role", "res"]
    else if (table == "activity") keys = ["id", "ds", "de", "vol"]

    let req = new Request(`ajax/${table}?${query}`)
    fetch(req).then(response => response.json()).then(data => {
        ReactDOM.render(
            React.createElement(Table, { "data": data, "table": table, "keys": keys, "query": query }, null),
            document.getElementById(`${table}_table_container`)
        );
    })

    if (table == "user") {
        ReactDOM.render(
            React.createElement("div"),
            document.getElementById("authority_table_container")
        );
    }
    if (table != "activity") {
        ReactDOM.render(
            React.createElement("div"),
            document.getElementById("activity_table_container")
        );
    }
}

ReactDOM.render(
    React.createElement(Form),
    document.getElementById("form_container")
);

updateData("user")