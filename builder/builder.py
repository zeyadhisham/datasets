import argparse
import yaml
from pprint import pprint

# Builder specific
from compile import Compiler
from auxiliary import to_dict


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description="The main Builder tool")

    parser.add_argument('spec', help='Location of a spec file.')
    parser.add_argument('-l', '--language', help='The desired output language bindings. If not present, defaults to all known languages.')
    parser.add_argument('-v', '--validate', help='Only validates the spec, skips compilation.', action='store_true')

    args = parser.parse_args()
    
    with open(args.spec, 'r') as specification_file:
        specification = yaml.load(specification_file)
        pprint(specification)
        compiled, warnings, errors = Compiler(specification).run()
        print("*"*10)
        for warning in warnings:
            print "Warning!", warning
        for error in errors:
            print "Error!", error
        print("*"*10)
        pprint(to_dict(compiled))