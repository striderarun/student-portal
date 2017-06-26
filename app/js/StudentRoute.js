export default class extends Relay.Route {
  static path = '/';
  static queries = {
    prefect: (Component) => Relay.QL`
        query RootQuery {
           prefect {
              ${Component.getFragment('prefect')},
           },
        }
    `,
  };
  static routeName = 'StudentRoute';
}
