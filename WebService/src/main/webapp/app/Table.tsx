import React from "react"
import { handler } from "./app"

interface TableProps {
    data: object[]
    table: string
    keys: string[]
    query: string
}

export default class Table extends React.Component<TableProps, {}> {
    constructor(props: TableProps) {
        super(props);
    }

    getKeys() {
        // return Object.keys(this.props.data[0]);
        return this.props.keys
    }

    getHeader() {
        var keys = this.getKeys();
        return keys.map((key: string) => {
            return <th key={key}>{key.toUpperCase()}</th>
        })
    }

    getRowsData() {
        var items = this.props.data;
        var keys = this.getKeys();
        return items.map((row: object, index: number) => {
            return (
                // @ts-ignore
                <tr key={index} onClick={() => { handler(this.props.table, row["id"]) }}>
                    {RenderRow({ key: index, data: row, keys: keys })}
                </tr>
            )
        })
    }

    render() {
        return (
            React.createElement("table", { "id": `${this.props.table}_table`, "query": this.props.query },
                [
                    React.createElement("thead", null, this.getHeader()),
                    React.createElement("tbody", null, this.getRowsData())
                ]
            )
        );
    }
}

interface RowData {
    key: any
    data: any
    keys: string[]
}

const RenderRow = (props: RowData) => {
    return props.keys.map((key) => {
        return <td key={props.data[key]}>{props.data[key]}</td>
    })
}
