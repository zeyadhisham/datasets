import sys as _sys
import os as _os
import json as _json
import sqlite3 as _sql
import difflib as _difflib

_HEADER = {'User-Agent': 
          'CORGIS Baseball library for educational purposes'}
_PYTHON_3 = _sys.version_info >= (3, 0)
_TEST = False
_HARDWARE = 1000

if _PYTHON_3:
    import urllib.request as _request
    from urllib.parse import quote_plus as _quote_plus
else:
    import urllib2 as _urllib2
    from urllib import quote_plus as _quote_plus

class DatasetException(Exception):
    ''' Thrown when there is an error loading the dataset for some reason.'''
    pass
    
_DATABASE_NAME = "baseball.db"
if _os.path.isfile(_DATABASE_NAME):
    _DATABASE = _sql.connect(_DATABASE_NAME)
else:
    raise DatasetException("Error! Could not find the \"{}\" file. Make sure that it is in the same directory as {}.py!".format(_DATABASE_NAME, __name__))

################################################################################
# Auxilary
################################################################################

def _parse_type(value, type_func):
    """
    Attempt to cast *value* into *type_func*, returning *default* if it fails.
    """
    default = type_func(0)
    if value is None:
        return default
    try:
        return type_func(value)
    except ValueError:
        return default
        
def _byteify(input):
    """
    Force the given input to only use `str` instead of `bytes` or `unicode`.
    This works even if the input is a dict, list,
    """
    if isinstance(input, dict):
        return {_byteify(key): _byteify(value) for key, value in input.items()}
    elif isinstance(input, list):
        return [_byteify(element) for element in input]
    elif _PYTHON_3 and isinstance(input, str):
        return str(input.encode('ascii', 'replace').decode('ascii'))
    elif not _PYTHON_3 and isinstance(input, unicode):
        return str(input.encode('ascii', 'replace').decode('ascii'))
    else:
        return input
        
def _guess_schema(input):
    if isinstance(input, dict):
        return {str(key.encode('ascii', 'replace').decode('ascii')): 
                _guess_schema(value) for key, value in input.items()}
    elif isinstance(input, list):
        return [_guess_schema(input[0])] if input else []
    else:
        return type(input)
        


################################################################################
# Domain Objects
################################################################################
    

    


################################################################################
# Interfaces
################################################################################



def get_player_by_name(name):
    """
    Returns information about a single player
    
    :param name: The name of the player.
    :type name: Str
    """
    
    # Match it against recommend values
    potentials = [r[0].lower() for r in _DATABASE.execute("SELECT DISTINCT name FROM players").fetchall()]
    if name.lower() not in potentials:
        best_guesses = _difflib.get_close_matches(name, potentials)
        if best_guesses:
            raise DatasetException("Error, the given identifier could not be found. Perhaps you meant one of:\n\t{}".format('\n\t'.join(map('"{}"'.format, best_guesses))))
        else:
            raise DatasetException("Error, the given identifier could not be found. Please check to make sure you have the right spelling.")
    
    
    
    if False:
        # If there was a Test version of this method, it would go here. But alas.
        pass
    
    else:
        rows = _DATABASE.execute("SELECT data FROM players WHERE name=? COLLATE NOCASE LIMIT 1".format(
            hardware=_HARDWARE),
            (name, ))
        data = [r[0] for r in rows]
        data = [_json.loads(r) for r in data]
        
        data = data[0]
        
        return _byteify(data)
        

def get_players(test=True):
    """
    Returns information about all the players.
    
    """
    
    
    if _TEST or test:
        rows = _DATABASE.execute("SELECT data FROM players LIMIT {hardware}".format(
            hardware=_HARDWARE))
        data = [r[0] for r in rows]
        data = [_json.loads(r) for r in data]
        
        return _byteify(data)
        
    else:
        rows = _DATABASE.execute("SELECT data FROM players".format(
            hardware=_HARDWARE))
        data = [r[0] for r in rows]
        data = [_json.loads(r) for r in data]
        
        return _byteify(data)
        

def get_team_by_name(name):
    """
    Returns information about a single team
    
    :param name: The name of the team.
    :type name: Str
    """
    
    # Match it against recommend values
    potentials = [r[0].lower() for r in _DATABASE.execute("SELECT DISTINCT name FROM teams").fetchall()]
    if name.lower() not in potentials:
        best_guesses = _difflib.get_close_matches(name, potentials)
        if best_guesses:
            raise DatasetException("Error, the given identifier could not be found. Perhaps you meant one of:\n\t{}".format('\n\t'.join(map('"{}"'.format, best_guesses))))
        else:
            raise DatasetException("Error, the given identifier could not be found. Please check to make sure you have the right spelling.")
    
    
    
    if False:
        # If there was a Test version of this method, it would go here. But alas.
        pass
    
    else:
        rows = _DATABASE.execute("SELECT data FROM teams WHERE name=? COLLATE NOCASE LIMIT 1".format(
            hardware=_HARDWARE),
            (name, ))
        data = [r[0] for r in rows]
        data = [_json.loads(r) for r in data]
        
        data = data[0]
        
        return _byteify(data)
        

def get_teams(test=True):
    """
    Returns information about all the teams.
    
    """
    
    
    if _TEST or test:
        rows = _DATABASE.execute("SELECT data FROM teams LIMIT {hardware}".format(
            hardware=_HARDWARE))
        data = [r[0] for r in rows]
        data = [_json.loads(r) for r in data]
        
        return _byteify(data)
        
    else:
        rows = _DATABASE.execute("SELECT data FROM teams".format(
            hardware=_HARDWARE))
        data = [r[0] for r in rows]
        data = [_json.loads(r) for r in data]
        
        return _byteify(data)
        

################################################################################
# Internalized testing code
################################################################################

def _test_interfaces():
    from pprint import pprint as _pprint
    from timeit import default_timer as _default_timer
    # Production test
    print("Production get_player_by_name")
    start_time = _default_timer()
    result = get_player_by_name('Jon Edward Zuber', )
    
    _pprint(result)
    
    print("Time taken: {}".format(_default_timer() - start_time))
    
    # Production test
    print("Production get_players")
    start_time = _default_timer()
    result = get_players(test=False)
    
    print("{} entries found.".format(len(result)))
    _pprint(_guess_schema(result))
    
    print("Time taken: {}".format(_default_timer() - start_time))
    # Test test
    print("Test get_players")
    start_time = _default_timer()
    result = get_players()
    
    print("{} entries found.".format(len(result)))
    _pprint(_guess_schema(result))
    
    print("Time taken: {}".format(_default_timer() - start_time))
    
    # Production test
    print("Production get_team_by_name")
    start_time = _default_timer()
    result = get_team_by_name('Philadelphia Phillies', )
    
    _pprint(result)
    
    print("Time taken: {}".format(_default_timer() - start_time))
    
    # Production test
    print("Production get_teams")
    start_time = _default_timer()
    result = get_teams(test=False)
    
    print("{} entries found.".format(len(result)))
    _pprint(_guess_schema(result))
    
    print("Time taken: {}".format(_default_timer() - start_time))
    # Test test
    print("Test get_teams")
    start_time = _default_timer()
    result = get_teams()
    
    print("{} entries found.".format(len(result)))
    _pprint(_guess_schema(result))
    
    print("Time taken: {}".format(_default_timer() - start_time))
    

if __name__ == '__main__':
    from optparse import OptionParser as _OptionParser
    _parser = _OptionParser()
    _parser.add_option("-t", "--test", action="store_true",
                      default=False,
                      help="Execute the interfaces to test them.")
    _parser.add_option("-r", "--reset", action="store_true",
                      default=False,
                      help="Reset the cache")
    (_options, _args) = _parser.parse_args()
    
    if _options.test:
        _test_interfaces()

    if _options.reset:
        _modify_self()