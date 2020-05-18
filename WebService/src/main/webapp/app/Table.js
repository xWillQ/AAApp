class Table extends React.Component {
    constructor(props) {
        super(props);
    }

    getKeys = function () {
        // return Object.keys(this.props.data[0]);
        return this.props.keys
    }

    getHeader = function () {
        var keys = this.getKeys();
        return keys.map((key, index) => {
            return React.createElement("th", { key: key }, key.toUpperCase())
        })
    }

    getRowsData = function () {
        var items = this.props.data;
        var keys = this.getKeys();
        return items.map((row, index) => {
            return React.createElement(
                "tr",
                { key: index },
                React.createElement(RenderRow, { key: index, data: row, keys: keys }, null)
            )
        })
    }

    render() {
        return (
            React.createElement("table", { "id": "table", "table": this.props.table, "query": this.props.query },
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
