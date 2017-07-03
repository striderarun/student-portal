import 'babel/polyfill';
import {Router, Route} from 'react-router';
import BrowserHistory from 'react-router/lib/BrowserHistory';
import StudentApp from './StudentApp';
import StudentDetail from './StudentDetail';


// A wrapper to create a Relay container
function createRelayContainer(Component, props) {
  if (Relay.isContainer(Component)) {
    // Construct the RelayQueryConfig from the route and the router props.
    var {name, queries} = props.route;
    var {params} = props;
    return (
      <Relay.RootContainer
        Component={Component}
        renderFetched={(data) => <Component {...props} {...data} />}
        route={{name, params, queries}}
      />
    );
  } else {
    return <Component {...props}/>;
  }
}

var StudentList = {
  students: (Component) => Relay.QL`
      query QueryType {
         students(ids:["1","2"]) {
            ${Component.getFragment('students')},
         },
      }
  `,
};

var StudentDetailQuery = {
  studentDetail: (Component) => Relay.QL`
    query {
      student(id: $id) {
        ${Component.getFragment('studentDetail')},
      },
    }
  `,
};

React.render(
  <Router
    history={new BrowserHistory()}
    createElement={createRelayContainer}>
    <Route>
      <Route
        name="StudentRoute"
        path="/"
        component={StudentApp}
        queries={StudentList}
      />
      <Route
        name="StudentDetail"
        path="/student/:id"
        component={StudentDetail}
        queries={StudentDetailQuery}
      />
    </Route>
  </Router>,
  document.getElementById('root')
);

