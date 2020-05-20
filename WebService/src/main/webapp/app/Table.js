import React from "react"

export default class Table extends React.Component {
    constructor(props) {
        super(props);
    }

    getKeys() {
        // return Object.keys(this.props.data[0]);
        return this.props.keys
    }

    getHeader() {
        var keys = this.getKeys();
        return keys.map((key, index) => {
            return React.createElement("th", { key: key }, key.toUpperCase())
        })
    }

    getRowsData() {
        var items = this.props.data;
        var keys = this.getKeys();
        return items.map((row, index) => {
            return React.createElement(
                "tr",
                { key: index, onClick: () => { handler(this.props.table, row["id"]) } },
                React.createElement(RenderRow, { key: index, data: row, keys: keys }, null)
            )
        })
    }

    render() {
        return (
            React.createElement("table", { "id": this.props.table + "_table", "query": this.props.query },
                [
                    React.createElement("thead", null, this.getHeader()),
                    React.createElement("tbody", null, this.getRowsData())
                ]
            )
        );
    }
}

const RenderRow = (props) => {
    return props.keys.map((key, index) => {
        return React.createElement("td", { key: props.data[key] }, props.data[key])
    })
}
