class StudentDetail extends React.Component {
  render() {
    var detail = this.props.studentDetail;
    console.log(detail);
    return (
        <div>
            <h1>Student Details</h1>
            <ul>
              <li>Id {detail.id}</li>
              <li>First Name: {detail.firstName}</li>
              <li>Last Name: {detail.lastName}</li>
              <li>Email: {detail.email}</li>
              <li>Grade: {detail.grade}</li>
              <li>Age: {detail.age}</li>
            </ul>
        </div>
    );
  }
}

export default Relay.createContainer(StudentDetail, {
  fragments: {
    studentDetail: () => Relay.QL`
      fragment on Student {
        id
        firstName
        lastName
        email
        grade
        age
      }
    `,
  },
});
