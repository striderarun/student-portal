export default class extends Relay.Route {
  static path = '/';
  static queries = {
    students: (Component) => Relay.QL`
        query QueryType {
           students(ids:["1","2"]) {
              ${Component.getFragment('students')},
           },
        }
    `,
  };
  static routeName = 'StudentRoute';
}
