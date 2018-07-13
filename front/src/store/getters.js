export default {
  elements: state => state.graphData,

  nodes: (state, { elements }) =>
    elements.filter(({ group }) => group === 'nodes'),

  edges: (state, { elements }) =>
    elements.filter(({ group }) => group === 'edges'),

  elementsData: (state, { elements }) => elements.map(d => d.data),

  nodesData: (state, { nodes }) => nodes.map(d => d.data),

  edgesData: (state, { edges }) => edges.map(d => d.data),

  elementsProp: (state, { elements }) => elements.map(d => d.data.prop),

  nodesProp: (state, { nodes }) => nodes.map(d => d.data.prop),

  edgesProp: (state, { edges }) => edges.map(d => d.data.prop),

  getSchema: ({ schema }) => label => {
    return schema.find(({ data: { id } }) => id === label);
  },
  getAttributes: (state, { getSchema }) => (label, attr) => {
    let currentSchema = getSchema(label);
    if (currentSchema) {
      if (typeof attr === 'string') {
        return currentSchema.data.attributes.find(({ name }) => name === attr);
      } else if (Array.isArray(attr)) {
        let attributes = currentSchema.data.attributes.filter(({ name }) =>
          attr.includes(name)
        );
        if (attributes.length > 0) {
          return attributes;
        }
      }
    }
    return undefined;
  },
  getAttr: (state, { getSchema }) => (label, extra = 'keyAttr') => {
    let currentSchema = getSchema(label);
    if (currentSchema) {
      return currentSchema.data.extraObj[extra];
    }
    return undefined;
  }
};
