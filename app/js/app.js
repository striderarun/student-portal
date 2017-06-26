import 'babel/polyfill';
import StudentApp from './StudentApp';
import StudentRoute from './StudentRoute';

React.render(
  <Relay.RootContainer Component={StudentApp} route={new StudentRoute()} />,
  document.getElementById('root')
);
