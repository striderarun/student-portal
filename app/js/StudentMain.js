import {Link} from 'react-router';

class StudentMain extends React.Component {
  render() {
    return (
        <Link to={`/student`}>
            <i className="icon big rounded color1 fa-cloud"/>
        </Link>
    );
  }
}

export default Relay.createContainer(StudentMain, {
  fragments: {
    students: () => Relay.QL`
      fragment on Student @relay(plural: true) {
        id
        firstName
        lastName
      }
    `,
  },
});
