package bestaveiro.autumncourse2017;

import java.util.ArrayList;

/**
 * esta classe vai servir de stack para saber quais os fragments que foram adicionados (selecionados
 * no navigation drawer) de maneira a voltar a selecioná-los no navigation drawer
 */
public class FragmentStack
{
    ArrayList<Integer> stack = new ArrayList<>();

    public void push(int a)
    {
        stack.add(a);
    }

    public int pop()
    {
        stack.remove(stack.size()-1);
        return stack.get(stack.size()-1);
    }
}
