const path = require('path');


module.exports = {
  entry: './WebService/src/main/webapp/app/index.js',
  output: {
    path: path.resolve(`${__dirname}/WebService/src/main/webapp/`, 'dist'),
    filename: 'bundle.js'
  }
};