import {Link} from 'react-router';

class StudentApp extends React.Component {
  render() {
    return (
        <div>
            <h1>Students</h1>
            <ul>
              {this.props.students.map(student =>
                <li key={student.id}>
                  <Link to={`/student/${student.id}`}>
                    {student.firstName} {student.lastName}
                  </Link>
                </li>
              )}
            </ul>
        </div>
    );
  }
}

export default Relay.createContainer(StudentApp, {
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
