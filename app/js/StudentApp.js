class StudentApp extends React.Component {
  render() {
    var rows = [];
    this.props.students.forEach((student) => {
      rows.push(<tr><th colSpan="2">{student.firstName}</th>
      <th colSpan="2">{student.lastName}</th></tr>);
    });
    return (
        <table>
            <thead>
              <tr>
                <th colSpan="2">First Name</th>
                <th colSpan="2">Last Name</th>
              </tr>
            </thead>
            <tbody>{rows}</tbody>
        </table>
    );
  }
}

export default Relay.createContainer(StudentApp, {
  fragments: {
    students: () => Relay.QL`
      fragment on Student @relay(plural: true) {
        firstName
        lastName
      }
    `,
  },
});
