class StudentApp extends React.Component {
  render() {
    var hello = this.props.prefect.firstName;
    return <h1>{hello}</h1>;
  }
}

export default Relay.createContainer(StudentApp, {
  fragments: {
    prefect: () => Relay.QL`
      fragment on Student {
        firstName
      }
    `,
  },
});
