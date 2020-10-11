package edu.pokemon.iut.tuttidex.ui.pokemonlist


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import edu.pokemon.iut.tuttidex.R
import edu.pokemon.iut.tuttidex.databinding.FragmentPokemonListBinding
import edu.pokemon.iut.tuttidex.ui.model.Pokemon
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 *
 */
class PokemonListFragment : Fragment() {
    //TODO 2 Declare une var(iable) privee qui sera init(ialiser) plus tard(late) du type FragmentPokemonListBinding
    private lateinit var  listBinding : FragmentPokemonListBinding

    val viewModel: PokemonListViewModel by viewModel()

    private var maxId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO 3 initialise la variable cree avec :
        // le LayoutInflater en parametre de onCreateView
        // le R.layout du fichier XML
        // le ViewGroup en parametre de onCreateView
        // on ne l'attache pas au parent

        listBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_pokemon_list,container,false)


        //TODO 4 instancie un PokemonsAdapter en lui passant la val(eur) viewModel et stocke l'instance dans une autre val(eur)

        val adapter = PokemonsAdapter(viewModel)

        //TODO 5 utilise la variable de type FragmentPokemonListBinding pour acceder à ta RecyclerView
        // initialise son attribut adapter

        listBinding.ListPokemon.adapter =adapter

        //TODO 6 utilise la variable de type FragmentPokemonListBinding pour acceder à ta RecyclerView
        // initialise son attribut layoutManager
        listBinding.ListPokemon.layoutManager=LinearLayoutManager(context)

        viewModel.pokemons.observe(viewLifecycleOwner, Observer<List<Pokemon>> { pokemons ->
            pokemons?.apply {
                //TODO 7 utilise la valeur de type PokemonsAdapter pour envoyer la liste des pokemons à la RecyclerView
                 listBinding.ListPokemon.adapter
            }
        })

        viewModel.maxPokemonId.observe(viewLifecycleOwner, Observer { maxId ->
            this.maxId = maxId
        })

        setHasOptionsMenu(true)
        // TODO 8 renvoie l'attribut root de ta variable de type FragmentPokemonListBinding à la place de null
        // (même si il y a un message d'erreur le .root existe est devrais fonctionner)

        return listBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = view?.findNavController()
        return if (navController != null) {
            NavigationUI.onNavDestinationSelected(item, navController)
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
